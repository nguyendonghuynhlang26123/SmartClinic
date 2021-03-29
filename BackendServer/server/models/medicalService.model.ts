import { MedicalServiceInterface } from '../interfaces';
import { Document } from 'mongoose';
import * as mongoose from 'mongoose';

const Schema = mongoose.Schema;
const MedicalServiceSchema = new Schema(
  {
    service_name: { type: String, required: true, unique: true },
    service_thumbnail: { type: String, default: '' },
    service_price: { type: Number, required: true },
    service_description: { type: String, default: '' },
    created_at: { type: Number },
    updated_at: { type: Number },
  },
  { timestamps: { createdAt: 'created_at', updatedAt: 'updated_at' } }
);

export const medicalServiceModel = mongoose.model<
  MedicalServiceInterface & Document
>('medical-services', MedicalServiceSchema);
