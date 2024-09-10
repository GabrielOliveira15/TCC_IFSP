# TCC_IFSP
Repositório para desenvolvimento do TCC.

## Como utilizar
- Crie um container no Docker utilizando esse comando:
`docker run --env=MYSQL_ROOT_PASSWORD=123 --env=PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin --env=GOSU_VERSION=1.17 --env=MYSQL_MAJOR=innovation --env=MYSQL_VERSION=9.0.1-1.el9 --env=MYSQL_SHELL_VERSION=9.0.1-1.el9 --volume=/var/lib/mysql --network=bridge -p 3307:3306 --restart=no --runtime=runc -d mysql:latest`.

- Após inicializado esse container, o MySQL responde na porta 3307, entre no MySQL dentro do container e crie um usuário `api` com a senha `123`, dê todas as permissões.

- Agora pode copiar esse repositório e iniciar, favor testar conexão com o banco;

## Tecnologias utilizadas
- Java 22
- Framework Spring 3.3.3
- MySQL
- Docker
- Autenticação e Autorização com JWT
