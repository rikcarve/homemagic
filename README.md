# homemagic
Secure webui for smart light-switches

## Build
Build and push image for raspi:
```bash
mvn deploy -Parm64
```

## Run
Login to raspi

```bash
docker stop homemagic
docker pull berry2:5000/homemagic-arm64
docker run -d -p 8080:8080 -p 8443:8443 --restart always --name homemagic berry2:5000/homemagic-arm64
```