# API
*src/lib/api.js* contains some methods for making requests to the backend server.
- `API.signup(userData, responseHandler)`
  - `userData` is an object of the form: `{ firstName, lastName, email, password }`.
  - `responseHandler` is a function that will be supplied the JSON body of the response

- `API.login(userData, responseHandler)`
  - `userData` is an object of the form: `{ email, password }`.
  - `responseHandler` is a function that will be supplied the JSON body of the response

# REST-related Todos
- [ ] Use or create some hooks to persist the `username, token, expiration` data that's returned from the `/api/v1/signup` and `api/v1/login` endpoints.
- [ ] Create more `API` methods to map to our REST backend (buying, selling, querying stocks).

---

This is a [Next.js](https://nextjs.org/) project bootstrapped with [`create-next-app`](https://github.com/vercel/next.js/tree/canary/packages/create-next-app).

## Getting Started

First, run the development server:

```bash
npm run dev
# or
yarn dev
# or
pnpm dev
```

## Learn More

- [Next.js Documentation](https://nextjs.org/docs) - learn about Next.js features and API.
- [Learn Next.js](https://nextjs.org/learn) - an interactive Next.js tutorial.

You can check out [the Next.js GitHub repository](https://github.com/vercel/next.js/) - your feedback and contributions are welcome!
