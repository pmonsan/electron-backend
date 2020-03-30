export interface ICountry {
  id?: number;
  code?: string;
  longLabel?: string;
  shortLabel?: string;
  active?: boolean;
  currencyId?: number;
}

export class Country implements ICountry {
  constructor(
    public id?: number,
    public code?: string,
    public longLabel?: string,
    public shortLabel?: string,
    public active?: boolean,
    public currencyId?: number
  ) {
    this.active = this.active || false;
  }
}
