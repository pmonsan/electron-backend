/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { BeneficiaryTypeDetailComponent } from 'app/entities/beneficiary-type/beneficiary-type-detail.component';
import { BeneficiaryType } from 'app/shared/model/beneficiary-type.model';

describe('Component Tests', () => {
  describe('BeneficiaryType Management Detail Component', () => {
    let comp: BeneficiaryTypeDetailComponent;
    let fixture: ComponentFixture<BeneficiaryTypeDetailComponent>;
    const route = ({ data: of({ beneficiaryType: new BeneficiaryType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [BeneficiaryTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BeneficiaryTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BeneficiaryTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.beneficiaryType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
