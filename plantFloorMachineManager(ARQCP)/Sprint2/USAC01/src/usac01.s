.section .data
    .equ INDICATOR_AND, '&'            # Define constant for '&' character
    .equ SEPARATOR, '#'                # Define constant for '#' character
    .equ SIZE_PREFIX_TEMP, 5           # Define size of the "TEMP" string
    .equ SIZE_PREFIX_HUM, 4            # Define size of the "HUM" string

TOKEN_TEMP:
    .asciz "TEMP"                      # String literal for "TEMP"

TOKEN_HUM:
    .asciz "HUM"                       # String literal for "HUM"

.section .text
    .global extract_data               # Declare the extract_data function as global

# %rdi -- STRING (Pointer to input string)
# %rsi -- TOKEN (Pointer to token, either "TEMP" or "HUM")
# %rdx -- UNIT (Pointer to store unit data)
# %rcx -- VALUE (Pointer to store value data)

extract_data:
    # prologue
    pushq %rbp                        # save the original value of RBP
    movq %rsp , %rbp                  # copy the current stack pointer to RBP
    
    call uppercase_token               # Convert TOKEN string to uppercase
    jmp check_token                    # Jump to token validation check

# Function to convert the TOKEN to uppercase
uppercase_token:
    movq $0, %r10                      # Initialize loop counter (r10)
loop_upper:
    cmpb $0, (%rsi,%r10,1)             # Compare current byte with null terminator
    je exit                            # Exit loop if end of string is reached
    cmpb $97, (%rsi,%r10,1)            # Check if character is lowercase ('a')
    jl skip                            # Skip if not lowercase
    cmpb $122, (%rsi,%r10,1)           # Check if character is lowercase ('z')
    jg skip                            # Skip if not lowercase

    subb $32, (%rsi,%r10,1)            # Convert to uppercase (ASCII value adjustment)
skip:
    incq %r10                          # Increment loop counter
    jmp loop_upper                     # Repeat the loop
exit:
    ret



# Function to check if the token matches "TEMP" or "HUM"
check_token:
    cmpb $'T', (%rsi)                  # Check if first byte is 'T' (for TEMP)
    je check_token_temp                # Jump to TEMP check if match
    cmpb $'H', (%rsi)                  # Check if first byte is 'H' (for HUM)
    je check_token_hum                 # Jump to HUM check if match

    jmp error                          # Jump to error if no match

check_token_hum:
    leaq TOKEN_HUM(%rip), %r8          # Load address of "HUM" token into %r8
    movq $0, %r10                      # Initialize loop counter
loop_hum:
    cmpq $SIZE_PREFIX_HUM, %r10        # Compare counter with size of "HUM"
    je valid_token                     # If matched, proceed to valid_token

    movb (%rsi,%r10,1), %bl            # Load byte from TOKEN to %bl
    cmpb %bl, (%r8,%r10,1)             # Compare byte with "HUM"
    jne error                          # Jump to error if mismatch

    incq %r10                          # Increment loop counter
    jmp loop_hum                       # Repeat loop

check_token_temp:
    leaq TOKEN_TEMP(%rip), %r8         # Load address of "TEMP" token into %r8
    movq $0, %r10                      # Initialize loop counter
loop_temp:
    cmpq $SIZE_PREFIX_TEMP, %r10       # Compare counter with size of "TEMP"
    je valid_token                     # If matched, proceed to valid_token

    movb (%rsi,%r10,1), %bl            # Load byte from TOKEN to %bl
    cmpb %bl, (%r8,%r10,1)             # Compare byte with "TEMP"
    jne error                          # Jump to error if mismatch

    incq %r10                          # Increment loop counter
    jmp loop_temp                      # Repeat loop



# If token is valid ("TEMP" or "HUM"), proceed to extract unit and value
valid_token:
    movq $0, %r10                      # Initialize loop counter
    jmp get_informations               # Jmp function to extract unit and value

    ret


# Function to extract information (unit and value) from string
get_informations:
    cmpb $INDICATOR_AND, (%rdi,%r10,1)  # Check if the current character is '&'
    je correct_string                   # Jump to correct_string if '&' found

    movb (%rdi,%r10,1), %bl             # Load byte from string into %bl
    cmpb %bl, (%rsi,%r10,1)             # Compare byte with TOKEN byte
    jne get_next_string                 # Jump to next string if mismatch

    incq %r10                           # Increment loop counter
    jmp get_informations                # Repeat loop


# Function to get the next string by skipping non-relevant characters
get_next_string:
    cmpb $0, (%rdi)                     # Check if end of string is reached
    je error                            # Jump to error if null terminator found
    cmpb $'#', (%rdi)                   # Check for '#' separator
    je prepare_string                   # Jump to prepare_string if '#' found

    incq %rdi                           # Increment pointer to next byte
    jmp get_next_string                 # Repeat loop


# Prepare for extracting unit or value after '#' symbol
prepare_string:
    incq %rdi                           # Skip '#' character
    jmp get_informations                # Continue extracting information


# Function to handle extraction of unit (e.g., "u")
correct_string:
    movq $0, %r10                       # Initialize loop counter
    
    call get_unit                       # Call get_unit to extract unit
    cmpb $0, (%rdx)                     # Check if unit prefix actually exist
    je error                            # Jump error
    movb $0, (%rdx,%r10,1)              # Null-terminate the unit string
    
    addq %r10, %rdi                     # Move rdi forward by unit length
    movq $0, %r10                       # Reset loop counter
    
    call get_value                      # Call get_value to extract value
    movl %eax, (%rcx)                   # Store the value in the provided location
    
    movl $1, %eax                      # Set return value to 1 (success)
    jmp epilogue



# Function to extract unit information (e.g., "u")
get_unit:
    cmpb $SEPARATOR, (%rdi)             # Check if current byte is '#'
    je error                            # If TRUE then means that no unit prefix was found
    cmpb $INDICATOR_AND, -1(%rdi)       # Check if last byte before current position is '&'
    jne skip_unit                       # If not '&', skip
    cmpb $'u', (%rdi)                   # Check if the unit is 'u'
    jne skip_unit                       # If not 'u', skip
    

    addq $5, %rdi                       # Move pointer past "u" prefix

copy_unit:
    cmpb $INDICATOR_AND, (%rdi,%r10,1)  # Check if unit extraction is complete ('&' found)
    je exit                             # Exit if '&' is encountered

    movb (%rdi,%r10,1), %bl             # Copy byte from string to %bl
    movb %bl, (%rdx,%r10,1)             # Store byte in unit string
    incq %r10                           # Increment loop counter
    jmp copy_unit                       # Repeat unit copy


skip_unit:
    addq $1, %rdi                       # Skip one byte and check again
    jmp get_unit


# Function to extract value information (e.g., "v")
get_value:
    cmpb $INDICATOR_AND, -1(%rdi)       # Check if last byte before current position is '&'
    jne skip_value                      # If not '&', skip
    cmpb $'v', (%rdi)                   # Check if the value is 'v'
    jne skip_value                      # If not 'v', skip

    addq $6, %rdi                       # Skip the "v" prefix
    movl $0, %eax                       # Initialize value to 0

copy_value:
    cmpb $SEPARATOR, (%rdi,%r10,1)      # Check if separator '#' is found
    je exit                             # Exit if separator is found
    cmpb $0, (%rdi,%r10,1)              # Check for null terminator
    je exit                             # Exit if null terminator is found

    jmp check_number                    # Check if the actual character is a number

check_number:
    cmpb $'0', (%rdi,%r10,1)            # If the actual character is less than '0' -> 48 ASCII
    jl next_number                      # If TRUE then it jumps to next number
    cmpb $'9', (%rdi,%r10,1)            # If the actual character is greater than '9' -> 57 ASCII
    jg next_number                      # If TRUE then it jumps to next number

    movb (%rdi,%r10,1), %bl             # Copy byte from string to %bl
    movsbl %bl, %ebx                    # Sign-extend byte into %ebx
    call calculate_num                  # Call function to calculate the numeric value

    incq %r10                           # Increment loop counter
    jmp copy_value                      # Repeat value copy

next_number:
    incq %r10                           # Increment loop counter
    jmp copy_value                      # Repeat value copy


skip_value:
    addq $1, %rdi                       # Skip one byte and check again
    jmp get_value

# Function to calculate the numerical value from the string digits
calculate_num:
    movl $10, %r15d                     # Set multiplier for decimal base (10)
    mull %r15d                          # Multiply current value by 10
    subl $'0', %ebx                     # Convert ASCII digit to integer
    addl %ebx, %eax                     # Add the value to the total
    ret

# Error handler function
error:
    movl $0, %eax                      # Set return value to 0 (indicating failure)
    movb $0, (%rdx)                    # Set the first byte of UNIT to null (empty string) 
    movl $0, (%rcx)                    # Set VALUE to 0 to indicate no valid value was found
    jmp epilogue                         


epilogue:
    movq %rbp, %rsp                    # retrieve the original RSP value
    popq %rbp                          # restore the original RBP value
    ret                                # Return from function
