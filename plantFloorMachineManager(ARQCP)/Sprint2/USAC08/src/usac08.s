.section .text
.global move_n_to_array

move_n_to_array:
    pushq %r8            # Salva o valor de %r8 na pilha (preservação de registradores usados)
    pushq %rsi           # Salva o valor de %rsi na pilha (preservação de registradores usados)

    call get_n_element   # Chama uma função auxiliar para obter o número de elementos no buffer

    popq %rsi            # Restaura o valor original de %rsi
    popq %r8             # Restaura o valor original de %r8

    cmpl $0, %eax        # Verifica se o retorno de get_n_element é <= 0 (nenhum elemento no buffer)
    jle error            # Se for <= 0, vai para a rotina de erro

    cmpl %eax, %r8d      # Compara o número de elementos requisitados (%r8d) com a quantidade disponível (%eax)
    jg error             # Se %r8d for maior que %eax, vai para a rotina de erro

    cmpl $0, %r8d        # Verifica se o número de elementos requisitados (%r8d) é negativo
    jl error             # Se for negativo, vai para a rotina de erro

    movl (%rdx), %ebx    # Carrega o valor de "tail" do buffer (posição atual de escrita)
    movl $0, %r10d       # Inicializa o contador de elementos removidos em 0

    subl $1, %esi        # Ajusta o índice para o limite do buffer (tail - 1)

    cmpl (%rcx), %ebx    # Compara a posição atual de leitura (head) com "tail"
    jg head_first        # Se "tail" já passou pelo fim do buffer, pula para o tratamento específico

loop_tail_first:
    cmpl %r10d, %r8d     # Verifica se já moveu o número necessário de elementos requisitados
    je loop_end          # Se o contador de elementos atingiu o necessário, sai do loop

    movl (%rdi, %rbx, 4), %r11d # Carrega o elemento do buffer (%rdi + %rbx * 4)
    movl %r11d, (%r9, %r10, 4)  # Copia o elemento para o array de destino (%r9 + %r10 * 4)

    incl %ebx            # Incrementa a posição do "tail"
    incl %r10d           # Incrementa o contador de elementos removidos

    jmp loop_tail_first  # Volta para continuar o loop

head_first:
    cmpl %r8d, %r10d     # Verifica se já moveu o número necessário de elementos requisitados
    je loop_end          # Se o contador de elementos atingiu o necessário, sai do loop

    movl (%rdi, %rbx, 4), %r11d # Carrega o elemento do buffer
    movl %r11d, (%r9, %r10, 4)  # Copia o elemento para o array de destino

    cmpl %esi, %ebx      # Verifica se "tail" alcançou o final do buffer
    je tail_restart       # Se sim, reinicia a partir do início do buffer

    incl %ebx            # Incrementa a posição do "tail"
    incl %r10d           # Incrementa o contador de elementos removidos

    jmp head_first       # Continua o loop

tail_restart:
    movl $0, %ebx        # Reinicia "tail" no início do buffer
    incl %r10d           # Incrementa o contador de elementos removidos

    jmp head_first       # Volta para continuar o loop

loop_end:
    cmpl %r8d, %eax      # Verifica se conseguiu mover o número requisitado de elementos
    je end_remove_all    # Se sim, vai para a rotina de finalização com sucesso

    movl %ebx, (%rdx)    # Atualiza o valor de "tail" no buffer
    movl $1, %eax        # Define retorno de sucesso (1)

    ret                  # Retorna

end_remove_all:
    movl %ebx, (%rdx)    # Atualiza o valor de "tail" no buffer
    movl $1, %eax        # Define retorno de sucesso (1)

    ret                  # Retorna

error:
    movl $0, %eax        # Define retorno de erro (0)

    ret                  # Retorna
