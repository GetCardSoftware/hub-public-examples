Ao revisar código, responda em **português** e foque em:

## Questões Críticas de Segurança

* Verifique se há segredos, chaves de API ou credenciais hardcoded
* Procure por vulnerabilidades de SQL Injection e XSS
* Confirme se há validação e sanitização adequadas de entradas
* Revise a lógica de autenticação e autorização

## Problemas de Desempenho

* Identifique problemas de consultas N+1 ao banco de dados
* Detecte loops ineficientes e questões algorítmicas
* Verifique vazamentos de memória e liberação de recursos
* Avalie oportunidades de cache para operações custosas

## Qualidade de Código

* As funções devem ser focadas e de tamanho apropriado
* Use nomes claros e descritivos
* Garanta tratamento de erros adequado em todo o código
* O código do projeto deve ser escrito em inglês, a menos que seja comentários e documentação

## Estilo de Revisão

* Seja específico e proponha ações concretas
* Explique o **porquê** das recomendações
* Reconheça bons padrões quando identificá-los
* Faça perguntas esclarecedoras quando a intenção do código não estiver clara

Sempre **priorize vulnerabilidades de segurança e problemas de desempenho** que possam impactar os
usuários.

Sempre **sugira melhorias de legibilidade**. Por exemplo, esta sugestão busca tornar o código mais
legível e também tornar a lógica de validação reutilizável e testável:

// Ao invés de:
if (user.email && user.email.includes('@') && user.email.length > 5) {
submitButton.enabled = true;
} else {
submitButton.enabled = false;
}

// Considere:
function isValidEmail(email) {
return email && email.includes('@') && email.length > 5;
}

submitButton.enabled = isValidEmail(user.email);
