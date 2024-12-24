.section .text
.global enqueue_value

enqueue_value:
    movq %rdi, %rax         # Buffer base address in %rax
    movl %esi, %r9d         # Length of buffer in %r9d
    movl (%rcx), %r10d      # Current head position in %r10d
    movl (%rdx), %r11d      # Current tail position in %r11d
    
    cmpl %r10d, %r11d       # Compare head and tail to see if buffer is empty
    je empty_buffer         # If equal, buffer is empty

    movl %r10d, %r12d       # Copy head to %r12d
    addl $1, %r12d          # Increment head position
    cmpl %r9d, %r12d        # Check if it exceeds buffer length
    jl head_wrap_check      # If not, continue
    movl $0, %r12d          # Wrap head to 0 if it reached length

head_wrap_check:
    movl %r8d, (%rax, %r10, 4)  # Insert value at buffer[head]
    movl %r12d, (%rcx)          # Update head position
    cmpl %r12d, %r11d           # Compare incremented head with tail
    je buffer_full              # If equal, buffer is full
    movl $0, %eax               # Return 0
    ret

buffer_full:
    addl $1, %r11d              # Increment tail position
    cmpl %r9d, %r11d            # Check if tail exceeds buffer length
    jl tail_wrap_check          # If not, continue
    movl $0, %r11d              # Wrap tail to 0 if it reached length

tail_wrap_check:
    movl %r11d, (%rdx)          # Update tail position
    movl $1, %eax               # Return 1 (buffer full)
    ret

empty_buffer:
    movl %r8d, (%rax, %r10, 4)  # Insert value at buffer[head]
    addl $1, %r10d              # Increment head index
    cmpl %r10d, %r9d            # Check if head exceeds buffer length
    jne update_head
    movl $0, %r10d              # Wrap head to 0 if it reached length

update_head:
    movl %r10d, (%rcx)          # Update head position
    movl $0, %eax               # Return 0
    ret
