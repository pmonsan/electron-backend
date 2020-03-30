export interface IPgData {
  id?: number;
  code?: string;
  longLabel?: string;
  shortLabel?: string;
  comment?: string;
  active?: boolean;
}

export class PgData implements IPgData {
  constructor(
    public id?: number,
    public code?: string,
    public longLabel?: string,
    public shortLabel?: string,
    public comment?: string,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
