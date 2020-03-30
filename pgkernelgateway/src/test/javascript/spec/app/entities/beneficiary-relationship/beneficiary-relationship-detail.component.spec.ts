/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { BeneficiaryRelationshipDetailComponent } from 'app/entities/beneficiary-relationship/beneficiary-relationship-detail.component';
import { BeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';

describe('Component Tests', () => {
  describe('BeneficiaryRelationship Management Detail Component', () => {
    let comp: BeneficiaryRelationshipDetailComponent;
    let fixture: ComponentFixture<BeneficiaryRelationshipDetailComponent>;
    const route = ({ data: of({ beneficiaryRelationship: new BeneficiaryRelationship(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [BeneficiaryRelationshipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BeneficiaryRelationshipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BeneficiaryRelationshipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.beneficiaryRelationship).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
