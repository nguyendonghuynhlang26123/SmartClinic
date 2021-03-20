import * as mongoose from "mongoose";
const Schema = mongoose.Schema;

const DoctorSchema = new Schema({
  avatar: { type: String, default: null },
  bio: { type: String, required: true },
  department: { type: String, required: true },
  hospital: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "hospitals",
    required: true,
  },
  created_at: { type: Number, default: Date.now() },
  updated_at: { type: Number, default: Date.now() },
});

export const doctor = mongoose.model("doctors", DoctorSchema);
