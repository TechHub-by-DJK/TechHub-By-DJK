# Frontend Integration: Cloudinary Image Uploads

This guide explains how to upload product, shop, and user profile images from the frontend (e.g., React) to your Spring Boot backend that stores files in Cloudinary.

## Overview
- Backend endpoints (authenticated):
  - POST `/api/upload/product`
  - POST `/api/upload/shop`
  - POST `/api/upload/profile`
- Request: `multipart/form-data` with field name `file`
- Accepted types: `jpg`, `jpeg`, `png`
- Response JSON: `{ "success": true, "url": "https://res.cloudinary.com/.../image.jpg" }`

## Prerequisites
- API base URL (examples use `http://localhost:5454`).
- A valid JWT token because `/api/**` routes are secured.
- CORS: allowed origins include `http://localhost:3000` (and your deployed frontend URL).

## Usage Pattern
- Always send a `multipart/form-data` request with a `file` field.
- Include header `Authorization: Bearer <token>`.

## Reusable uploader (Axios)
```ts
// src/api/upload.ts
import axios from 'axios';

const API_BASE = process.env.REACT_APP_API_BASE_URL || 'http://localhost:5454';

export async function uploadImage(
  file: File,
  type: 'product' | 'shop' | 'profile',
  token: string,
  onProgress?: (percent: number) => void
): Promise<string> {
  const formData = new FormData();
  formData.append('file', file);

  const res = await axios.post(
    `${API_BASE}/api/upload/${type}`,
    formData,
    {
      headers: {
        Authorization: `Bearer ${token}`,
        // Do NOT set Content-Type manually; let the browser set multipart boundary
      },
      onUploadProgress: evt => {
        if (onProgress && evt.total) {
          const percent = Math.round((evt.loaded * 100) / evt.total);
          onProgress(percent);
        }
      },
    }
  );

  if (res.data?.success && res.data?.url) {
    return res.data.url as string;
  }
  throw new Error('Upload failed');
}
```

## React component example
```tsx
// src/components/ImageUploader.tsx
import React, { useState } from 'react';
import { uploadImage } from '../api/upload';

type Props = {
  type: 'product' | 'shop' | 'profile';
  token: string; // JWT
  onUploaded?: (url: string) => void;
};

export default function ImageUploader({ type, token, onUploaded }: Props) {
  const [preview, setPreview] = useState<string | null>(null);
  const [progress, setProgress] = useState<number>(0);
  const [error, setError] = useState<string | null>(null);
  const [uploading, setUploading] = useState(false);

  function validate(file: File) {
    const allowed = ['image/jpeg', 'image/png', 'image/jpg'];
    if (!allowed.includes(file.type)) {
      throw new Error('Only JPG, JPEG, PNG are allowed');
    }
    // Optional size limit example: 5MB
    const max = 5 * 1024 * 1024;
    if (file.size > max) {
      throw new Error('Max size 5MB');
    }
  }

  async function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
    const file = e.target.files?.[0];
    if (!file) return;

    setError(null);
    setProgress(0);
    setUploading(true);

    try {
      validate(file);
      setPreview(URL.createObjectURL(file));
      const url = await uploadImage(file, type, token, setProgress);
      onUploaded?.(url);
    } catch (err: any) {
      setError(err.message || 'Upload error');
    } finally {
      setUploading(false);
    }
  }

  return (
    <div>
      <input
        type="file"
        accept="image/png,image/jpeg"
        onChange={handleChange}
        disabled={uploading}
      />

      {preview && (
        <div style={{ marginTop: 8 }}>
          <img src={preview} alt="preview" style={{ maxWidth: 200, maxHeight: 200 }} />
        </div>
      )}

      {uploading && <div>Uploading... {progress}%</div>}
      {error && <div style={{ color: 'red' }}>{error}</div>}
    </div>
  );
}
```

Usage:
```tsx
// Product image
<ImageUploader type="product" token={token} onUploaded={(url) => setProductImage(url)} />

// Shop image
<ImageUploader type="shop" token={token} onUploaded={(url) => setShopImage(url)} />

// Profile picture
<ImageUploader type="profile" token={token} onUploaded={(url) => setAvatarUrl(url)} />
```

## Fetch alternative (no Axios)
```ts
const API_BASE = process.env.REACT_APP_API_BASE_URL || 'http://localhost:5454';

export async function uploadWithFetch(
  file: File,
  type: 'product' | 'shop' | 'profile',
  token: string
) {
  const formData = new FormData();
  formData.append('file', file);

  const res = await fetch(`${API_BASE}/api/upload/${type}`, {
    method: 'POST',
    headers: { Authorization: `Bearer ${token}` },
    body: formData,
  });
  if (!res.ok) throw new Error('Upload failed');
  const data = await res.json();
  if (data?.success && data?.url) return data.url as string;
  throw new Error('Upload failed');
}
```

## cURL test
```bash
curl -X POST http://localhost:5454/api/upload/product \
  -H "Authorization: Bearer <token>" \
  -F "file=@/path/to/image.png"
```

## Error handling
- 400: invalid file type or missing file.
- 500: upload failure.
- Always check `data.success` and ensure `data.url` is present.

## Persisting URLs
- After success, save the returned `url` to your backend models (product, shop, or user profile) using your existing create/update APIs.

