.section .text
.global dequeue_value

dequeue_value:
	pushq %r8
	pushq %rsi
    call get_n_element
    popq %rsi
    popq %r8
	
    cmpl $0, %eax
    jle fail
    
    movl (%rdx), %ebx
    cmpl (%rcx), %ebx
    jl tail_before_head

tail_at_end:
	movslq %ebx, %rbx
	movl (%rdi, %rbx, 4), %r9d
	movl %r9d, (%r8)
	movq $0, %rbx
	movl (%rdi, %rdx, 4), %r9d
	movl %r9d, (%rdx)
	movl $1, %eax
	ret


tail_before_head:
	movslq %ebx, %rbx
	movl (%rdi, %rbx, 4), %r9d
	movl %r9d, (%r8)
	incl (%rdx)
	movl $1, %eax
	ret
	
fail:
	ret

