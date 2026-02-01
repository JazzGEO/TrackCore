import psycopg2
from datetime import datetime
from decimal import Decimal
from decouple import config

DB_HOST = config('DB_HOST')
DB_PORT = config('DB_PORT')
DB_NAME = config('DB_NAME')
DB_USER = config('DB_USER')
DB_PASSWORD = config('DB_PASSWORD')

DEPRECIATION_RATES = {
    'notebook': 0.20,
    'monitor': 0.10,
    'celular': 0.25,
    'impressora': 0.15,
    'servidor': 0.20
}

def connect_db():
    return psycopg2.connect(
        host=DB_HOST,
        port=DB_PORT,
        database=DB_NAME,
        user=DB_USER,
        password=DB_PASSWORD
    )

def calculate_depreciation():
    conn = connect_db()
    cursor = conn.cursor()
    
    cursor.execute("""
        SELECT id, name, category, purchase_value, purchase_date, current_value
        FROM assets
        WHERE status != 'baixado'
    """)
    
    assets = cursor.fetchall()
    updated_count = 0
    
    for asset in assets:
        asset_id, name, category, purchase_value, purchase_date, current_value = asset
        
        rate = DEPRECIATION_RATES.get(category.lower(), 0.10)
        
        months_old = (datetime.now().date() - purchase_date).days / 30
        years_old = months_old / 12
        
        depreciation_amount = purchase_value * Decimal(rate) * Decimal(years_old)
        new_value = max(purchase_value - depreciation_amount, purchase_value * Decimal(0.1))
        
        if new_value != current_value:
            cursor.execute("""
                UPDATE assets
                SET current_value = %s, updated_at = CURRENT_TIMESTAMP
                WHERE id = %s
            """, (new_value, asset_id))
            
            print(f"Atualizado: {name} | Valor: {purchase_value} -> {new_value}")
            updated_count += 1
    
    conn.commit()
    cursor.close()
    conn.close()
    
    print(f"\nTotal de ativos atualizados: {updated_count}")

if __name__ == "__main__":
    print("Iniciando cálculo de depreciação...")
    calculate_depreciation()
    print("Cálculo concluído!")