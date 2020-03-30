/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { MeansofpaymentDetailComponent } from 'app/entities/meansofpayment/meansofpayment-detail.component';
import { Meansofpayment } from 'app/shared/model/meansofpayment.model';

describe('Component Tests', () => {
  describe('Meansofpayment Management Detail Component', () => {
    let comp: MeansofpaymentDetailComponent;
    let fixture: ComponentFixture<MeansofpaymentDetailComponent>;
    const route = ({ data: of({ meansofpayment: new Meansofpayment(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [MeansofpaymentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(MeansofpaymentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MeansofpaymentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.meansofpayment).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
