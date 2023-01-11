import dayjs from 'dayjs';
import { ITransferDocumentItem } from 'app/shared/model/transfer-document-item.model';
import { IBusinessYear } from 'app/shared/model/business-year.model';
import { IStorage } from 'app/shared/model/storage.model';
import { IBusinessPartner } from 'app/shared/model/business-partner.model';
import { TransferDocumentType } from 'app/shared/model/enumerations/transfer-document-type.model';
import { TransferDocumentStatus } from 'app/shared/model/enumerations/transfer-document-status.model';

export interface ITransferDocument {
  id?: number;
  type?: TransferDocumentType;
  transferDate?: string;
  status?: TransferDocumentStatus;
  accountingDate?: string | null;
  reversalDate?: string | null;
  transferDocumentItems?: ITransferDocumentItem[] | null;
  businessYear?: IBusinessYear;
  receivingStorage?: IStorage | null;
  dispatchingStorage?: IStorage | null;
  businessPartner?: IBusinessPartner | null;
}

export const defaultValue: Readonly<ITransferDocument> = {};
