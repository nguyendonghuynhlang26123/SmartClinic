import * as express from "express";
import { connectMongodb } from "./connectMongo";

async function initServer() {
  const app = express();
  const port = process.env.PORT || 8002;

  connectMongodb();

  app.get("/", (req, res) => {
    res.send("Hello World!");
  });

  app.listen(port, () => {
    console.log(`Server is listening at http://localhost:${port}`);
  });
}

initServer();
