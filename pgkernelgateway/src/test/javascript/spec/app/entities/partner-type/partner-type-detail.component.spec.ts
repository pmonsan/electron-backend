/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PartnerTypeDetailComponent } from 'app/entities/partner-type/partner-type-detail.component';
import { PartnerType } from 'app/shared/model/partner-type.model';

describe('Component Tests', () => {
  describe('PartnerType Management Detail Component', () => {
    let comp: PartnerTypeDetailComponent;
    let fixture: ComponentFixture<PartnerTypeDetailComponent>;
    const route = ({ data: of({ partnerType: new PartnerType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PartnerTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PartnerTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PartnerTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.partnerType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
