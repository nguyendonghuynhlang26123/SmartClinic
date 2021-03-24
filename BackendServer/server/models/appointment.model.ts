import * as mongoose from "mongoose";

const Schema = mongoose.Schema;
const AppointmentSchema = new Schema(
  {
    doctor: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "doctors",
      required: true,
    },
    patient: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "patients",
      required: true,
    },
    time: { type: Number, required: true },
    is_expired: {
      type: Boolean,
      required: true,
    },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: "created_at", updatedAt: "updated_at" } }
);

export const appointmentModel = mongoose.model("appointments", AppointmentSchema);
