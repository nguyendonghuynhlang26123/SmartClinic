import * as express from "express";
import { PrescriptionService } from "./prescription.service";
const router = express.Router();

const prescriptionService: PrescriptionService = new PrescriptionService();

router.get("/", async (req, res) => {
  const prescriptions = await prescriptionService.getAllPrescription();
  res.json(prescriptions);
});

router.get("/:prescription_id", async (req, res) => {
  const prescription = await prescriptionService.getPrescriptionById(
    req.params.prescription_id
  );
  res.json(prescription);
});

export const PrescriptionController = router;