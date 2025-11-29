#!/bin/bash

# Danh sách họ
LAST_NAMES=("Nguyễn" "Trần" "Lê" "Phạm" "Hoàng" "Võ" "Vũ" "Đặng" "Bùi" "Đỗ")

# Danh sách tên đệm
MID_NAMES=("Văn" "Thị" "Hữu" "Minh" "Quang" "Ngọc" "Đình" "Anh" "Phúc" "Gia")

# Danh sách tên chính
FIRST_NAMES=("An" "Bình" "Chi" "Dũng" "Hà" "Hưng" "Khánh" "Linh" "Nam" "Trang" "Tuấn" "Vy")

# Tỷ lệ email sai (20%)
ERROR_RATE=20

# Ghi header CSV
echo "id,name,email" > users.csv

for i in $(seq 1 100000)
do
  # Random họ - đệm - tên
  ln=${LAST_NAMES[$RANDOM % ${#LAST_NAMES[@]}]}
  mn=${MID_NAMES[$RANDOM % ${#MID_NAMES[@]}]}
  fn=${FIRST_NAMES[$RANDOM % ${#FIRST_NAMES[@]}]}

  fullname="$ln $mn $fn"

  # Random quyết định email lỗi hay đúng
  r=$((RANDOM % 100))
  if [[ $r -lt $ERROR_RATE ]]; then
      # Email lỗi: thiếu @
      email="user${i}example.com"
  else
      # Email đúng
      email="user${i}@example.com"
  fi

  echo "$i,\"$fullname\",$email" >> users.csv
done
