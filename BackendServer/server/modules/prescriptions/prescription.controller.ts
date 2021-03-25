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

router.post("/", async (req, res) => {
  try {
    const prescription = await prescriptionService.createPrescription(req.body);
    res.json(prescription);
  } catch (err) {
    console.log(err);
    res.status(400).json({ message: err.message });
  }
});

router.put("/:prescription_id", async (req, res) => {
  try {
    const result = await prescriptionService.updatePrescriptionById(
      req.params.prescription_id,
      req.body
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

router.delete("/:prescription_id", async (req, res) => {
  try {
    const result = await prescriptionService.deletePrescription(
      req.params.prescription_id
    );
    res.json(result);
  } catch (err) {
    console.log(err);
    res.status(404).json({ message: err.message });
  }
});

export const PrescriptionController = router;
