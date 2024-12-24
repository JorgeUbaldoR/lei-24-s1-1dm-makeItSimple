.section .text
.global get_n_element

get_n_element:
	movl $0, %eax
		
	cmpl $0, %esi
	je is_empty

	cmpl $0, (%rdx)
	jl fail
	
	cmpl $0, (%rcx)
	jl fail

	cmpl %esi, (%rdx)
	jge fail
	
	cmpl %esi, (%rcx)
	jge fail
	
	
	movl (%rdx), %r8d
	cmpl %r8d, (%rcx)
	je is_empty
	
	movl (%rcx), %r8d
	incl %r8d
	cmpl %r8d, (%rdx)
	je is_full

	decl %esi
	cmpl %esi, (%rdx)
	je tail_at_end
	incl %esi
	movl (%rcx), %r8d
	cmpl (%rdx), %r8d
	jg tail_first

head_first:
	movl (%rdx), %r8d
	subl %r8d, %esi
	movl (%rcx), %eax
	addl %esi, %eax
	ret


tail_first:
	movl (%rdx), %r8d
	movl (%rcx), %eax
	subl %r8d, %eax
	ret

tail_at_end:
	incl %esi
	movl (%rcx), %eax
	incl %eax
	ret
	
is_empty:
	movl $0, %eax
	ret

is_full:
	movl %esi, %eax
	ret

end:
	ret

fail:
	movl $-1, %eax
	ret
