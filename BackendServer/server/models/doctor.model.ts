import * as mongoose from "mongoose";

const Schema = mongoose.Schema;
const DoctorSchema = new Schema(
  {
    avatar: { type: String, default: null },
    bio: { type: String, required: true, unique: true },
    department: { type: String, required: true },
    hospital: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "hospitals",
      required: true,
    },
    token: { type: String, required: true },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: "created_at", updatedAt: "updated_at" } }
);

export const doctorModel = mongoose.model("doctors", DoctorSchema);
