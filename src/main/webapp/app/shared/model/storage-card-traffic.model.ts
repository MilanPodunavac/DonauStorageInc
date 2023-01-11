import dayjs from 'dayjs';
import { IStorageCard } from 'app/shared/model/storage-card.model';
import { StorageCardTrafficType } from 'app/shared/model/enumerations/storage-card-traffic-type.model';
import { StorageCardTrafficDirection } from 'app/shared/model/enumerations/storage-card-traffic-direction.model';

export interface IStorageCardTraffic {
  id?: number;
  type?: StorageCardTrafficType;
  direction?: StorageCardTrafficDirection;
  amount?: number;
  price?: number;
  value?: number;
  document?: string | null;
  date?: string | null;
  storageCard?: IStorageCard;
}

export const defaultValue: Readonly<IStorageCardTraffic> = {};
