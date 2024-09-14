FROM mysql:latest

LABEL maintainerName="Gabriel Nascimento"
LABEL maintainerEmail="gabrielnascimento751@outlook.com"

# Setar variáveis de ambiente
ENV MYSQL_ROOT_PASSWORD=123456
ENV MYSQL_USER=api
ENV MYSQL_PASSWORD=123

# Executar comandos
RUN echo 'Atualizando pacotes'
RUN apt-get update

RUN echo 'Entrando no MySQL'
RUN mysql -u root -p
RUN ${MYSQL_ROOT_PASSWORD}

RUN echo 'Criando usuário e dando permissões'
RUN CREATE USER ${MYSQL_USER}@% IDENTIFIED BY ${MYSQL_PASSWORD};
RUN GRANT ALL PRIVILEGES ON *.* TO ${MYSQL_USER}@%;

RUN echo 'Recarregando permissões'
RUN FLUSH PRIVILEGES;

# Expor porta
EXPOSE 3306