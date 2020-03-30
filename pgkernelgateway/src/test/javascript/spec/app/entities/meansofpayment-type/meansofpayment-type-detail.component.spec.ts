/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { MeansofpaymentTypeDetailComponent } from 'app/entities/meansofpayment-type/meansofpayment-type-detail.component';
import { MeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';

describe('Component Tests', () => {
  describe('MeansofpaymentType Management Detail Component', () => {
    let comp: MeansofpaymentTypeDetailComponent;
    let fixture: ComponentFixture<MeansofpaymentTypeDetailComponent>;
    const route = ({ data: of({ meansofpaymentType: new MeansofpaymentType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [MeansofpaymentTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MeansofpaymentTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeansofpaymentTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.meansofpaymentType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
