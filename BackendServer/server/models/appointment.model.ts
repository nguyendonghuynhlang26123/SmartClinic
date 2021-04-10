import * as mongoose from 'mongoose';

const Schema = mongoose.Schema;
const AppointmentSchema = new Schema(
  {
    doctor: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'doctors',
      required: true,
    },
    patient: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'patients',
      required: true,
    },
    time: { type: String, required: true },
    date: { type: String, required: true },
    service: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'medical-services',
      required: true,
    },
    note: { type: String, default: false },
    status: {
      type: String,
      default: 'PENDING',
      enum: ['PENDING', 'CANCELED', 'PROCESSED'],
    },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: 'created_at', updatedAt: 'updated_at' } }
);

export const appointmentModel = mongoose.model(
  'appointments',
  AppointmentSchema
);
