# Membros do grupo
Vinicius Ambrosi

# Encode & Decode 
Este trabalho inclui tratamento de encoding e decode para as  estratégias Unary, Golomb, Fibonnaci, Elias Gamma e Delta. Junto, inclui também a implementação de algorítmos de ruído como CRC8 e Hamming, sendo o CRC8 aplicado no cabeçalho de 16 bits (2 bytes) e o hamming aplicado em cada codeword gerado pelo meio de encoding.

# Desenvolvimento
O trabalho foi desenvolvido em Java e Maven, sendo estes uma depêndencia para a execução do projeto.

# Execução
De forma a executar o projeto é necessario:
* Clonar o projeto
* Ir ao diretório do projeto no terminal
* Rodar mvn clean install package 
   * Clean - limpar targets
   * Install - instala dependências do gerenciador maven
   * Package - gera .jar na pasta targets para execução
* Pode optar agora por rodar, utilizando:
   * O jar manualmente com 'java -jar "nome_do_jar.jar" <caminho_do_arquivo> <modo_a_ser_executado> <código>' localizado na pasta target OU
   * Os targets to maven com 'mvn exec:java -Dexec.mainClass=App "-Dexec.args=<caminho_do_arquivo> <modo_a_ser_executado> <código>".
* Executar um dos comandos acima, sendo que os argumentos são baseados em:
   * caminho_do_arquivo é o caminho absoluto de um arquivo txt
   * modo_a_ser_executado é ENCODE ou DECODE - opções definidas em [Modos](https://github.com/ViniciusAmbrosi/encode-decode/blob/main/src/main/java/enumerators/OperationTypeEnum.java)
   * código é UNARY, GOLOMB, FIBONACCI, ELIASGAMMA ou DELTA - opções definnidas em [Códigos](https://github.com/ViniciusAmbrosi/encode-decode/blob/main/src/main/java/enumerators/EncodeDecodeStrategyEnum.java)
* Deve ver na pasta resources durante ENCODE arquivos surgirem com nome de encode.ecc e encode.cod
* Deve ver na pasta resources durante DECODE um arquivo com nome decode.txt surgir
