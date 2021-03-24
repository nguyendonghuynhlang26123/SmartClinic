import * as mongoose from 'mongoose';

const Schema = mongoose.Schema;
const HospitalSchema = new Schema(
  {
    name: { type: String, required: true, unique: true },
    location: {
      type: {
        cor_x: {
          type: Number,
          required: true,
        },
        cor_y: {
          type: Number,
          required: true,
        },
      },
      required: true,
    },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: 'created_at', updatedAt: 'updated_at' } }
);

export const hospitalModel = mongoose.model('hospitals', HospitalSchema);
