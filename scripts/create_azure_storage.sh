#!/bin/bash
resourceGroup="mf-music"

az group create \
  --name $resourceGroup \
  --location westeurope

sleep 20s

az storage account create \
  --name mfmusicsa \
  --resource-group $resourceGroup \
  --location westeurope \
  --sku Standard_LRS \
  --kind StorageV2 \
  --access-tier Cool

sleep 30s

az storage container create \
  --name mfmusicsa-blob \
  --account-name mfmusicsa \
  --public-access blob
