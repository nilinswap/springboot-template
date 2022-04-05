import os
import psycopg2

conn = psycopg2.connect(
        host="localhost",
        database="sbtemplate",
        user="sbtemplate",
        password="sbtemplate")

# Open a cursor to perform database operations
cur = conn.cursor()

cur.execute('''
SELECT *
FROM pg_catalog.pg_tables
WHERE schemaname != 'pg_catalog' AND 
    schemaname != 'information_schema';
''')
print(cur)

cur.execute('''
select * from django_migrations;
''')

for record in cur:
        print(record)