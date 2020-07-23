echo getting credentials and starting the new image
aws configure set default.region us-east-1

docker pull spangly4sho/iggybot:latest

docker run -d --env jda.discord.token=$(aws secretsmanager get-secret-value --secret-id prod/iggybot-token --query SecretString --output text | jq -r ."TOKEN") --name iggybot spangly4sho/iggybot

sleep 5

docker ps

echo done starting new image