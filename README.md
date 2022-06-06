# threat-dashboard
Scans twitter and other sources for CVE mentions

## Development

### Database

To run a database locally and seed it with some data execute the folloing commands from the database directory:

```shell
  docker-compose up
```

```shell
  ./setup.sh
```

### Scripts

To install the dependencies run:

```
pip install tabulate
pip install psutil
pip install psycopg2-binary
pip install PyGithub
pip install --user --upgrade git+https://github.com/twintproject/twint.git@origin/master#egg=twint
```

## Production
### Build
#### Frontend client

To build the frontend client as a Docker container for use in production run the following command:

```shell
  env TAG=1 COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f build.yml build
```

### Deploy

```shell
  env SERVER_VERSION=1 CLIENT_VERSION=1 docker-compose up
```

or for swarm

```shell
  env SERVER_VERSION=1 CLIENT_VERSION=1 docker stack deploy --compose-file=docker-compose.yml threat-dashboard
```

