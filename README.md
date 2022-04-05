# springboot-template

To connect is psql inside docker

first do 
`su postgres`
then 
run `psql`

to see if psql server is up `pg_isready -h localhost -p 5432`

hooks?

[] write docker-compose to share volume with migrations folder and run migrations.