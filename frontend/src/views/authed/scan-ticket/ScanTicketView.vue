<template>
  <div class="container">
    <!-- Page Header -->
    <h2 class="title">Scan Ticket</h2>

    <!-- Camera Placeholder -->
    <div v-if="!cameraActive" class="camera-placeholder">
      <p v-if="!cameraPermission">Camera access is required. Click "Start Scanning" to request permission.</p>
      <button @click="startCamera" class="start-btn">Start Scanning</button>
    </div>

    <!-- Camera Scanner -->
    <div v-else class="scanner-container">
      <!-- Placeholder for Camera View -->
      <video ref="camera" autoplay class="camera-view"></video>
      <div class="overlay-text">Scanning...</div>
    </div>

    <!-- Status -->
    <div v-if="statusMessage" :class="['status-message', statusType]">
      {{ statusMessage }}
    </div>
  </div>
</template>

<script>
import { ticketService } from "@/services/tickets";
import QrScanner from "qr-scanner";

export default {
  name: "ScanTicketView",
  data() {
    return {
      cameraActive: false,
      scanningEnabled: true,
      statusMessage: "",
      statusType: "",
      cameraPermission: false, // Track camera permission status
    };
  },
  methods: {
    async startCamera() {
      // Request camera permission if not already granted
      if (!this.cameraPermission) {
        const permissionGranted = await this.requestCameraPermission();
        if (!permissionGranted) {
          this.statusMessage = "Camera access is required.";
          this.statusType = "error";
          return;
        }
      }

      this.cameraActive = true;
      await this.initCamera();
    },

    // Request camera permission
    async requestCameraPermission() {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({video: true});
        // If permission is granted, stop the stream immediately
        stream.getTracks().forEach(track => track.stop());
        this.cameraPermission = true;
        return true;
      } catch (error) {
        console.error("Camera permission error:", error);
        this.cameraPermission = false;
        return false;
      }
    },

    async initCamera() {
      try {
        const stream = await navigator.mediaDevices.getUserMedia({video: {facingMode: "environment"}});
        this.$refs.camera.srcObject = stream;
        await this.scanQRCode();
      } catch (error) {
        this.statusMessage = "Camera access denied.";
        this.statusType = "error";
        console.error(error);
      }
    },

    async scanQRCode() {
      const qrScanner = new QrScanner(this.$refs.camera, this.onBarcodeScanned, {
        highlightScanRegion: true,
        highlightCodeOutline: true,
      });
      await qrScanner.start();
    },

    async onBarcodeScanned(result) {
      if (!this.scanningEnabled) return;

      try {
        this.scanningEnabled = false;
        const data = result.data;

        // Parse data
        const [ticket, owner] = data.split(",");
        const ticketId = parseInt(ticket.split(":")[1]);
        const ownerId = parseInt(owner.split(":")[1]);

        await ticketService.validateOne(ticketId, ownerId);

        this.statusMessage = "Ticket validated successfully!";
        this.statusType = "success";
      } catch (error) {
        this.statusMessage = "Failed to validate ticket.";
        this.statusType = "error";
        console.error(error);
      } finally {
        this.scanningEnabled = true;
      }
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.camera-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 400px;
  border: 2px dashed #ccc;
  border-radius: 8px;
  color: #666;
}

.start-btn {
  margin-top: 10px;
  padding: 10px 20px;
  background-color: #4caf50;
  color: #fff;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
}

.start-btn:hover {
  background-color: #45a049;
}

.scanner-container {
  position: relative;
}

.camera-view {
  width: 100%;
  height: 400px;
  border-radius: 8px;
}

.overlay-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 10px 20px;
  border-radius: 8px;
  font-size: 18px;
}

.status-message {
  margin-top: 20px;
  font-size: 16px;
}

.status-message.success {
  color: green;
}

.status-message.error {
  color: red;
}
</style>
