export interface IFeature {
  id?: number;
  code?: string;
  comment?: string;
  longLabel?: string;
  shortLabel?: string;
  active?: boolean;
}

export class Feature implements IFeature {
  constructor(
    public id?: number,
    public code?: string,
    public comment?: string,
    public longLabel?: string,
    public shortLabel?: string,
    public active?: boolean
  ) {
    this.active = this.active || false;
  }
}
