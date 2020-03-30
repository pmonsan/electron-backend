export interface IPartner {
  id?: number;
  code?: string;
  name?: string;
  apiKey?: string;
  address?: string;
  city?: string;
  postalCode?: string;
  countryCode?: string;
  rsaPublicKeyPath?: string;
  contactFirstname?: string;
  contactLastname?: string;
  businessEmail?: string;
  businessPhone?: string;
  partnerTypeCode?: string;
  active?: boolean;
  partnerSecurityId?: number;
}

export class Partner implements IPartner {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public apiKey?: string,
    public address?: string,
    public city?: string,
    public postalCode?: string,
    public countryCode?: string,
    public rsaPublicKeyPath?: string,
    public contactFirstname?: string,
    public contactLastname?: string,
    public businessEmail?: string,
    public businessPhone?: string,
    public partnerTypeCode?: string,
    public active?: boolean,
    public partnerSecurityId?: number
  ) {
    this.active = this.active || false;
  }
}
