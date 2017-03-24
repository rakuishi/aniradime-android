# http://zipcloud.ibsnet.co.jp/

require 'csv'
require 'sqlite3'
require 'nkf'

def nkf(str)
  NKF.nkf('-W -w -X -m0 -Z1 -h1', str)
end

db = SQLite3::Database.new('x-ken-all.db')
db.execute('CREATE TABLE postalcode (code varchar(16), prefecture_id int, city_id int, prefecture varchar(16), city varchar(16), street varchar(16), prefecture_yomi varchar(16), city_yomi varchar(16), street_yomi varchar(16));')
db.execute('CREATE INDEX idx_search on postalcode (code, prefecture, city, street);')
db.execute('CREATE INDEX idx_prefecture_id on postalcode (prefecture_id);')
db.execute('CREATE INDEX idx_city_id on postalcode (city_id);')

prefecture_id = 0
city_id = 0
pre_prefecture = ''
pre_city = ''

CSV.read('x-ken-all.csv', encoding: 'Shift_JIS:UTF-8', headers: false).each do |data|
  next if data[2].length == 0

  code = data[2]
  prefecture_yomi = nkf(data[3])
  city_yomi = nkf(data[4])
  street_yomi = nkf(data[5])
  prefecture = data[6]
  city = data[7]
  street = data[8]

  if prefecture != pre_prefecture then
    prefecture_id += 1
    pre_prefecture = prefecture
  end

  if city != pre_city then
    city_id += 1
    pre_city = city
  end

  db.execute('INSERT INTO postalcode (code, prefecture_id, city_id, prefecture, city, street, prefecture_yomi, city_yomi, street_yomi) values (?, ?, ?, ?, ?, ?, ?, ?, ?)', code, prefecture_id, city_id, prefecture, city, street, prefecture_yomi, city_yomi, street_yomi)
  print '.'
end

db.close
