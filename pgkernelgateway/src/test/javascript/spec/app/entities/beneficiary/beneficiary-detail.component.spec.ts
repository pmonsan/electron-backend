/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { BeneficiaryDetailComponent } from 'app/entities/beneficiary/beneficiary-detail.component';
import { Beneficiary } from 'app/shared/model/beneficiary.model';

describe('Component Tests', () => {
  describe('Beneficiary Management Detail Component', () => {
    let comp: BeneficiaryDetailComponent;
    let fixture: ComponentFixture<BeneficiaryDetailComponent>;
    const route = ({ data: of({ beneficiary: new Beneficiary(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [BeneficiaryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BeneficiaryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BeneficiaryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.beneficiary).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
