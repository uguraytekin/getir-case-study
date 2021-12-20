FROM mongo:latest
RUN echo creating auth folds
RUN mkdir /auth
COPY file.key /auth/file.key
RUN chmod 600 /auth/file.key
RUN chown 999:999 /auth/file.key