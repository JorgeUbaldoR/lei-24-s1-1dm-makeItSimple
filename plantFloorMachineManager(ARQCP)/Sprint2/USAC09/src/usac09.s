.section .text
.global sort_array

sort_array:             
    movl $0, %eax
	cmpl %esi, %eax
	je fail

loop1:
    cmpl %esi, %eax               
    jge end 
    movslq %eax, %r10             
    movl (%rdi, %r10, 4), %ecx                
    movl %eax, %r8d               
    addl $1, %r8d      
                      
loop2:
    cmpl %esi, %r8d                
    jge next_value                       
         
    movslq %r8d, %r11             
    movl (%rdi, %r11, 4), %r9d     

	cmpb $1, %dl
	je verify
    cmpl %ecx, %r9d
    jg swap_descending

return:
    incl %r8d                     
    jmp loop2                      

swap_descending:
    movl %ecx, (%rdi, %r11, 4)      
    movl %r9d, (%rdi, %r10, 4)  
    movl %r9d, %ecx   
    jmp loop2

swap_ascending:
	movl %ecx, (%rdi, %r11, 4)      
    movl %r9d, (%rdi, %r10, 4)  
    movl %r9d, %ecx
    jmp loop2
	

next_value:
    incl %eax                      
    jmp loop1  

verify:
	cmpl %ecx, %r9d         
	jl swap_ascending
	jmp return

end:
	movl $1, %eax
    ret                             
    
fail:
	ret

