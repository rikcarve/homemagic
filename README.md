# homemagic
Secure webui for smart light-switches

## Build
Build and push image for raspi:
```bash
docker pull --platform linux/arm64/v8 eclipse-temurin:21-jre

mvn package docker:build docker:push -Parm64
```

## Run
Login to raspi

```bash
docker stop homemagic
docker pull berry2:5000/homemagic-arm64
docker run -d -p 8080:8080 -p 8443:8443 --restart always --name homemagic berry2:5000/homemagic-arm64
```

## Native image

Workarounds for WSL (rancher desktop): switch to buildkit and symlink c
```bash
wsl -d rancher-desktop
vi /etc/docker/daemon.json
ln -s /mnt/c c
```

daemon.json:
```json
{
  "features": {
    "buildkit": true
  },
  "insecure-registries": [
    "berry2.fritz.box:5000"
  ]
}
```
