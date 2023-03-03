import { IBusinessContact } from 'app/shared/model/business-contact.model';
import { ILegalEntity } from 'app/shared/model/legal-entity.model';
import { ITransferDocument } from 'app/shared/model/transfer-document.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IBusinessPartner {
  id?: number;
  businessContact?: IBusinessContact;
  legalEntityInfo?: ILegalEntity;
  transfers?: ITransferDocument[] | null;
  company?: ICompany;
}

export const defaultValue: Readonly<IBusinessPartner> = {};
