.section .text
   .global median  
   .global sort_array


# Parameters:
# %rdi -- vec (pointer to the array)
# %esi -- length (number of elements in the array)
# %rdx -- me (pointer to where the median will be stored)

median:
   # prologue
   pushq %rbp                       # save the original value of RBP
   movq %rsp,%rbp                   # copy the current stack pointer to RBP

   movq %rdx, %r15                  # Save the pointer to `me` (from %rdx) in %r15 for later use
   jmp check_length

check_length:
   test %rdi, %rdi                  # Check if pointer is null
   jz error                         # If zero flag is zero then pointer is null
   cmpl $0, %esi                    # Check if length is less than or equal to zero
   jle error                        # If length <= 0, jump to error

   movb $'1', %dl                   # Set %dl to a placeholder character '1' (for sorting purposes)
   
   call sort_array                  # Call the function to sort the array in ascending order

   cmpl $0, %eax                    # Check if sorting was successful (assuming sort_array returns 0 on failure)
   je error                         # If sort_array failed, jump to error

   
   jmp get_median                   # Proceed to calculate the median
    
get_median:                         # Calculate the midpoint index for odd-length arrays: length / 2
   movl $2, %ebx                    # Divisor for midpoint calculation
   movl %esi, %eax                  # Copy length to %eax for division

   cdq                              # Sign extend %eax to %edx:eax
   divl %ebx                        # Divide %eax by 2, quotient in %eax, remainder in %edx

   
   movslq %eax, %r8                 # Move the quotient to %r8, representing the midpoint index

   cmpl $0, %edx                    # Check if length is even (if remainder %edx is 0)
   je length_even                   # If even, jump to length_even handling

                                    # If length is odd, get the element at the midpoint index as the median
   movl (%rdi,%r8,4), %ebx          # Load the value at vec[midpoint] into %ebx
   movl %ebx, (%r15)                # Store this value into the location pointed to by %r15 (me)

   jmp success                      # Jump to success exit

length_even:                        # If length is even, calculate median as the average of the two middle values
   movl (%rdi,%r8,4), %eax          # Load the element at vec[midpoint] into %eax
   addl -4(%rdi,%r8,4), %eax        # Add the element at vec[midpoint - 1] to %eax

   cdq                              # Sign extend %eax for division
   idivl %ebx                       # Divide by 2 to get the average
   movl %eax, (%r15)                # Store the average into the location pointed to by %r15 (me)
   jmp success                      # Jump to success exit

success:                            # If everything succeeded, set return value to 1 (indicating success)
   movl $1, %eax
   jmp epilogue

error:                              # If there was an error (length <= 0 or sort_array failure), return 0
   movl $0, %eax
   jmp epilogue

epilogue:
   movq %rbp, %rsp                  # retrieve the original RSP value
   popq %rbp                        # restore the original RBP value
   ret                              # Return from the function