## Notes

To test this service a Redis server must be running. Easiest way it to use docker:

```docker
docker run -it --rm -p 6379:639 redis
```

Note that this will remove the container on exit (see the `--rm` option there).