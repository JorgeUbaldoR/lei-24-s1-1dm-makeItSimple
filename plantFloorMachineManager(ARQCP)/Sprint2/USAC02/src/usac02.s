.section .data
    .equ MIN_NUM, 0         # Minimum allowed number
    .equ MAX_NUM, 31        # Maximum allowed number
    .equ SIZE, 5            # Size of the array to store binary digits

.section .text
   .global get_number_binary        # Declare the global entry point of the function


get_number_binary:
    # prologue
    pushq %rbp                      # save the original value of RBP
    movq %rsp,%rbp                  # copy the current stack pointer to RBP

    movq $0, %r8                    # Initialize index register to 0
    call initialize_zeros           # Call a function to initialize the array to zeros
    
    cmpl $MIN_NUM, %edi             # Compare the input number with the minimum allowed value
    jl error                        # Jump to error if the number is less than MIN_NUM
    cmpl $MAX_NUM, %edi             # Compare the input number with the maximum allowed value
    jg error                        # Jump to error if the number is greater than MAX_NUM

    movl %edi, %eax                 # Move the input number into %eax
    movl $2, %ebx                   # Set divisor (2) in %ebx for binary division
    jmp get_binary                  # Jump to the binary conversion routine


initialize_zeros:
    cmpq $SIZE, %r8                 # Compare index with SIZE
    jge exit_zeros                  # If index >= SIZE, exit
    
    movb $0, (%rsi, %r8, 1)         # Store zero at the memory location pointed by %rsi + index
    incq %r8                        # Increment the index register
    jmp initialize_zeros            # Repeat the loop
    
exit_zeros:
    movq $SIZE-1, %r8               # Set the index to SIZE-1
    ret                             # Return from the function

 
get_binary:
    cmp $0, %eax                    # Compare %eax with zero
    je end_binary                   # If zero, jump to end_binary (number is fully converted)

    cdq                             # Sign-extend %eax into %edx for division
    divl %ebx                       # Divide %eax by 2, result in %eax, remainder in %edx

    test %edx, %edx                 # Test if the remainder is zero
    jz skip                         # If remainder is zero, skip the next step

    movb $1, (%rsi, %r8, 1)         # If remainder is 1, store it in the array at the current index

skip:
    decq %r8                        # Decrement the index
    jmp get_binary                  # Repeat the binary conversion loop

end_binary:
    movl $1, %eax                   # Set %eax to 1 to indicate successful conversion 
    jmp epilogue                             

error:
    movb $0, (%rsi)
    movl $0, %eax                   # Set %eax to 0 to indicate an error (invalid number)
    jmp epilogue                             


epilogue:
    movq %rbp, %rsp                    # retrieve the original RSP value
    popq %rbp                          # restore the original RBP value
    ret                                # Return from the function