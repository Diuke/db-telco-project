wget https://bin.equinox.io/c/4VmDzA7iaHb/ngrok-stable-linux-amd64.tgz
tar -xf ngrok-stable-linux-amd64.tgz
rm ngrok-stable-linux-amd64.tgz
sudo chmod 777 ./ngrok
./ngrok authtoken 1mL4mYn9MHTHFgoW3uTq6Y9H217_2iZJRggUXRjp96nryDJJj
clear
docker-compose up -d
./ngrok tcp 3306