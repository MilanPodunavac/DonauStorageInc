import { ITransferDocument } from 'app/shared/model/transfer-document.model';
import { IResource } from 'app/shared/model/resource.model';

export interface ITransferDocumentItem {
  id?: number;
  amount?: number;
  price?: number;
  value?: number | null;
  transferDocument?: ITransferDocument;
  resource?: IResource;
}

export const defaultValue: Readonly<ITransferDocumentItem> = {};
