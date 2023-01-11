export interface IContactInfo {
  id?: number;
  email?: string;
  phoneNumber?: string;
}

export const defaultValue: Readonly<IContactInfo> = {};
