import * as mongoose from "mongoose";
import * as dotenv from "dotenv";

export function connectMongodb() {
  dotenv.config();

  const {
    MONGO_USERNAME,
    MONGO_PASSWORD,
    MONGO_HOSTNAME,
    MONGO_DATABASE,
  } = process.env;

  const MONGO_URL = `mongodb+srv://${MONGO_USERNAME}:${MONGO_PASSWORD}@${MONGO_HOSTNAME}/${MONGO_DATABASE}?retryWrites=true&w=majority`;

  mongoose
    .connect(MONGO_URL, {
      useCreateIndex: true,
      useNewUrlParser: true,
      useUnifiedTopology: true,
    })
    .then(() => {
      console.log("Database connection successfully!");
    })
    .catch((err) => {
      console.log(err);
      console.log("Database connection error!");
    });

  require("mongoose").Promise = global.Promise;
}
