.section .bss
.global aux
	.comm aux, 8
	
.section .data
.equ SPACE, 32
.equ TAB, 9

.section .text
.global get_number

get_number:
	movq $0, %rbx
	leaq aux(%rip), %rcx
	movq $0, %rdx
	movq $0, %r15
	movl $0, %r10d
	movq $0, %r14 #not used
	movl $0, %r13d


loop:
	cmpb $0, (%rdi, %rbx, 1)
	je get_int
	cmpb $'0', (%rdi, %rbx, 1)
	jl verify_space
	cmpb $'9', (%rdi, %rbx,1)
	jg fail
	incl %r13d
	movb (%rdi, %rbx, 1), %r8b
	subb $'0', %r8b
	cmpq $0, %r14 #not used
	movsbl %r8b, %r8d
	movl %r8d, (%rcx, %rdx, 4)
	incq %rdx
continue:
	incq %rbx
	jmp loop

verify_space:
	cmpb $SPACE, (%rdi, %rbx, 1)
	je continue
	cmpb $TAB, (%rdi, %rbx, 1)
	je continue
	jmp fail


get_int:
	movl $10, %r9d
	movq %rdx, %r11
	movl (%rcx, %r15, 4), %r8d
	decq %r11
	cmpq $1, %r11 
	je one_expo
	cmpq $0, %r11
	je zero_expo
power:
	imull %r9d, %r8d
	decq %r11
	cmpq $0, %r11
	jg power

do_sum:
	addl %r8d, %r10d
	incq %r15
	decq %rdx
	cmpq $0, %rdx
	jg get_int
	jmp end
	
one_expo:
	movl $10, %r9d
	movl (%rcx, %r15, 4), %r14d
	imull %r9d, %r14d
	addl %r14d, %r10d
	incq %r15
zero_expo:
	addl (%rcx, %r15, 4), %r10d
	
end:
	cmpl $0, %r13d
	je fail
	movl %r10d, (%rsi)
	movl $1, %eax
	ret

fail:
	movl $0, %eax
	ret
