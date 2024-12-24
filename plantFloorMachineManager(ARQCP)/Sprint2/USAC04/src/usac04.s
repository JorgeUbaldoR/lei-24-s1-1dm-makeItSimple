.section .bss
    .comm array_temp, 256          # Allocate 256 bytes for the temporary array

.section .text
    .global format_command
    .extern get_number_binary

format_command:
    movq %rdi, %rbx                   # Copy the input string pointer to %rbx for processing
    movq %rdi, %rcx                   # Copy the input string pointer to %rcx for scanning
    movq $5, %r15                     # Set the maximum number of binary values to process in %r15d
    movq $0, %r14                     # Initialize the processed count in %r14d to 0

check_empty:
	movb (%rbx), %al                  # Load the first character of the input string
    cmpb $0, %al                      # Check if the first character is the null terminator
    je return_zero                    # If null terminator, return 0 immediately

trim_start:
    movb (%rcx), %al                  # Load the current character from the string into %al
    cmpb $0, %al                      # Check if the current character is the null terminator
    je end                            # If null terminator, end processing
    cmpb $' ', %al                    # Check if the character is a space
    je skip_start                     # If a space, skip it and continue scanning

copy_characters:
    movb (%rcx), %al                  # Copy the current character to %al
    cmpb $0, %al                      # Check if the current character is the null terminator
    je trim_end                       # If null terminator, start trimming the end of the string
    movb %al, (%rbx)                  # Copy the character from %al to the destination string
    addq $1, %rbx                     # Increment the destination pointer
    addq $1, %rcx                     # Increment the source pointer
    jmp copy_characters               # Continue copying characters

skip_start:
    addq $1, %rcx                     # Increment the source pointer to skip the space
    jmp trim_start                    # Restart the loop

trim_end:
    subq $1, %rbx                     # Move the destination pointer back one position

trim_check:
    cmpb $' ', (%rbx)                 # Check if the last character is a space
    jne end_with_null                 # If not a space, end the string
    subq $1, %rbx                     # Otherwise, backtrack the pointer
    jmp trim_check                    # Repeat the check

end_with_null:
    addq $1, %rbx                     # Move the pointer forward to write the null terminator
    movb $0, (%rbx)                   # Write the null terminator at the end of the string
    movq %rdi, %rcx                   # Reload the input string pointer into %rcx
    movb (%rcx), %al                  # Read the first character of the input string
    cmpb $'o', %al                    # Check if the first character is 'o'
    je check_second_char              # If 'o', check the next character
    cmpb $'O', %al                    # Check if the first character is 'O'
    jne not_match                     # If not, the string does not match

check_second_char:
    addq $1, %rcx                     # Move to the next character
    movb (%rcx), %al                  # Load the second character
    cmpb $'p', %al                    # Check if it is 'p'
    je match_op                       # If 'p', handle "OP"
    cmpb $'P', %al                    # Check if it is 'P'
    je match_op                       # If 'P', handle "OP"
    cmpb $'n', %al                    # Check if it is 'n'
    je match_on                       # If 'n', handle "ON"
    cmpb $'N', %al                    # Check if it is 'N'
    je match_on                       # If 'N', handle "ON"
    cmpb $'f', %al                    # Check if it is 'f'
    je match_of                      # If 'f', handle "OF"
    cmpb $'F', %al                    # Check if it is 'F'
    jne not_match                     # If not a valid match, mark as no match

not_match:
    movb $0, (%rdx)                   # Write null terminator to the output
    movl $0, %eax                     # Return 0 (no match)
    ret                               # Exit

match_op:
	addq $1, %rcx                     # Move to the next character
    movb (%rcx), %al                  # Load the second character
    cmpb $0, %al
    jne not_match
    movb $'O', (%rdx)                 # Write "O" to the output
    addq $1, %rdx                     # Advance output pointer
    movb $'P', (%rdx)                 # Write "P" to the output
    addq $1, %rdx                     # Advance output pointer
    jmp append_prefix                 # Append the prefix

match_on:
	addq $1, %rcx                     # Move to the next character
    movb (%rcx), %al                  # Load the second character
    cmpb $0, %al
    jne not_match
    movb $'O', (%rdx)                 # Write "O" to the output
    addq $1, %rdx                     # Advance output pointer
    movb $'N', (%rdx)                 # Write "N" to the output
    addq $1, %rdx                     # Advance output pointer
    jmp append_prefix                 # Append the prefix

match_of:
	addq $1, %rcx                     # Move to the next character
    movb (%rcx), %al                  # Load the second character
    cmpb $'f', %al                    # Check if it is 'f'
    je match_off                     # If 'f', handle "OF"
    cmpb $'F', %al                    # Check if it is 'F'
    je match_off
	jmp not_match
	
match_off:
	addq $1, %rcx                     # Move to the next character
    movb (%rcx), %al                  # Load the second character
    cmpb $0, %al
    jne not_match
    movb $'O', (%rdx)                 # Write "O" to the output
    addq $1, %rdx                     # Advance output pointer
    movb $'F', (%rdx)                 # Write "F" to the output
    addq $1, %rdx                     # Advance output pointer
    movb $'F', (%rdx)                 # Write "F" to the output
    addq $1, %rdx                     # Advance output pointer
    jmp append_prefix                 # Append the prefix
    
append_prefix:
    #movb $' ', (%rdx)                 # Write a space
    #addq $1, %rdx                     # Advance output pointer
    movb $',', (%rdx)                 # Write a comma
    addq $1, %rdx                     # Advance output pointer
    movq %rsi, %rdi                   # Move binary output buffer pointer to %rdi
    movq %rdx, %rsi                   # Move result pointer to %rsi
    pushq %rdx                        # Save current output pointer
    call get_number_binary            # Call binary conversion function
    popq %rdx                         # Restore output pointer

    # Copy cmd to array_temp in reverse order
    call copy_cmd_to_array_temp

    # Add spaces and commas between characters in cmd
    call add_spaces_and_commas
	movl $1, %eax
    ret                              # Exit the function

copy_cmd_to_array_temp:
    movq %rdx, %rsi                # Pointer to `cmd` (source) in %rsi
    lea array_temp(%rip), %rdi     # Pointer to `array_temp` (destination) in %rdi

    addq $4, %rsi                  # Point to the last element in the source array (index 4)
    movq $5, %rcx                  # Set the loop counter to 5

reverse_copy_loop:
    movb (%rsi), %al               # Load a byte from the current position in `cmd`
    addb $'0', %al                 # Convert the numeric value to ASCII ('0' or '1')
    movb %al, (%rdi)               # Copy the ASCII value to `array_temp`
    addq $1, %rdi                  # Increment the destination pointer
    subq $1, %rsi                  # Decrement the source pointer
    decq %rcx                      # Decrement the loop counter
    jnz reverse_copy_loop          # Repeat the loop if %rcx is not zero

    movb $0, (%rdi)                # Null-terminate the copied data
    ret                            # Return to the caller

add_spaces_and_commas:
    lea array_temp(%rip), %rsi     # Pointer to `array_temp` (source) in %rsi
    movq %rdx, %rdi                # Pointer to `cmd` (destination) in %rdi

process_temp_loop:
    movb (%rsi), %al               # Load a byte from `array_temp` into %al
    cmpb $0, %al                   # Check if it is the null terminator
    je finalize_cmd                # If null terminator, we are done

    movb %al, (%rdi)               # Write the character to `cmd`
    addq $1, %rdi                  # Advance the `cmd` pointer

    cmpb $0, 1(%rsi)               # Peek at the next character in `array_temp`
    je skip_formatting             # If next is null terminator, skip formatting

    # Add a space and a comma
    #movb $' ', (%rdi)              # Write a space
    #addq $1, %rdi                  # Advance the `cmd` pointer
    movb $',', (%rdi)              # Write a comma
    addq $1, %rdi                  # Advance the `cmd` pointer

skip_formatting:
    addq $1, %rsi                  # Advance the `array_temp` pointer
    jmp process_temp_loop          # Repeat the loop

finalize_cmd:
    movb $0, (%rdi)                # Null-terminate the modified `cmd`
    ret                            # Return to the caller

return_zero:
    movq %rdx, %rdi                   # Move the destination pointer (%rdx) into %rdi
    movb $0, (%rdi)                   # Write null terminator to the destination buffer
    movl $0, %eax                     # Set return value to 0
    ret                               # Return to the caller
