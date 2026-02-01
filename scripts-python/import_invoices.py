import psycopg2
import pandas as pd
from datetime import datetime
from decouple import config

DB_HOST = config('DB_HOST')
DB_PORT = config('DB_PORT')
DB_NAME = config('DB_NAME')
DB_USER = config('DB_USER')
DB_PASSWORD = config('DB_PASSWORD')

def connect_db():
    return psycopg2.connect(
        host=DB_HOST,
        port=DB_PORT,
        database=DB_NAME,
        user=DB_USER,
        password=DB_PASSWORD
    )

def import_from_csv(file_path):
    df = pd.read_csv(file_path)
    
    required_columns = ['name', 'category', 'purchase_value', 'purchase_date']
    for col in required_columns:
        if col not in df.columns:
            raise ValueError(f"Coluna obrigatória ausente: {col}")
    
    conn = connect_db()
    cursor = conn.cursor()
    
    imported_count = 0
    
    for index, row in df.iterrows():
        try:
            cursor.execute("""
                INSERT INTO assets (name, category, purchase_value, purchase_date, current_value, status, created_at, updated_at)
                VALUES (%s, %s, %s, %s, %s, 'disponivel', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
            """, (
                row['name'],
                row['category'],
                float(row['purchase_value']),
                row['purchase_date'],
                float(row['purchase_value'])
            ))
            
            print(f"Importado: {row['name']} - R$ {row['purchase_value']}")
            imported_count += 1
            
        except Exception as e:
            print(f"Erro ao importar linha {index + 1}: {e}")
    
    conn.commit()
    cursor.close()
    conn.close()
    
    print(f"\nTotal de ativos importados: {imported_count}")

if __name__ == "__main__":
    import sys
    
    if len(sys.argv) < 2:
        print("Uso: python import_invoices.py <caminho_do_arquivo.csv>")
        sys.exit(1)
    
    file_path = sys.argv[1]
    
    print(f"Importando notas fiscais de: {file_path}")
    import_from_csv(file_path)
    print("Importação concluída!")